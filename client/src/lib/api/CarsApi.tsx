import { useMutation, useQuery } from "react-query";
import { toast } from "sonner";
import qs from "query-string";
import { useNavigate, useSearchParams } from "react-router-dom";
import { CarFormValues } from "@/pages/cars/routes/create-car/CreateCarPage";
import { errorCatch } from "../utils";
import { CARS_API_ROUTE } from "../consts";
import { Car, CarsGETManyRes } from "../types";
import queryClient from "./queryClient";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const GET_CARS = "getCars";
const GET_CAR = "getCar";

export const useGetCars = () => {
  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get("page")) - 1 || 0;
  const search = searchParams.get("search");
  const sortBy = searchParams.get("sortBy");
  const url = qs.stringifyUrl({
    url: `${API_BASE_URL}/${CARS_API_ROUTE}`,
    query: {
      page,
      sortBy,
      search,
    },
  }, { skipNull: true });
  const getCarsReq: () => Promise<CarsGETManyRes> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get cars");
    }
    return res.json();
  };
  const {
    data: fetchedCars, isLoading, isError, error,
  } = useQuery([GET_CARS, url], getCarsReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedCars, isLoading, isError, error,
  };
};

export const useGetCar = (id?: string | null) => {
  const url = `${API_BASE_URL}/${CARS_API_ROUTE}/${id}`;
  const getCarsReq: () => Promise<Car> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error(`Failed to get car with id ${id}`);
    }
    return res.json();
  };
  const {
    data: fetchedCar, isLoading, isError, error,
  } = useQuery([url, GET_CAR], getCarsReq, {
    enabled: !!id,
  });
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    fetchedCar, isLoading, isError, error,
  };
};

export const useCreateCar = () => {
  const createCarReq = async (values: CarFormValues): Promise<Car> => {
    const form = {
      ...values,
      ownerId: values.owner.id,
      makeId: values.make.id,
      modelId: values.model.id,
      colorId: values.color.id,
    };
    const body = JSON.stringify({
      ...form,
    });
    // if the car is created and its id is set in the create car page,
    // then react-query should allow it to be fetched automatically
    const res = await fetch(`${API_BASE_URL}/${CARS_API_ROUTE}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body,
    });
    if (!res.ok) {
      throw new Error("failed to create car");
    }
    const json = await res.json();
    return json;
  };
  const {
    mutateAsync: createCar, isLoading, isError, isSuccess,
  } = useMutation(createCarReq);
  return {
    createCar, isLoading, isError, isSuccess,
  };
};

export const useUpdateCar = (id: string) => {
  const updateCarReq = async (values: CarFormValues): Promise<void> => {
    const form = {
      ...values,
      ownerId: values.owner.id,
      makeId: values.make.id,
      modelId: values.model.id,
      colorId: values.color.id,
    };
    const body = JSON.stringify({
      ...form,
    });
    const res = await fetch(`${API_BASE_URL}/${CARS_API_ROUTE}/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body,
    });
    if (!res.ok) {
      throw new Error("failed to update car");
    }
  };
  const {
    mutateAsync: updateCar, isLoading, isError, isSuccess,
  } = useMutation(updateCarReq);
  return {
    updateCar, isLoading, isError, isSuccess,
  };
};

export const useDeleteCar = (id: number, returnTo?: string) => {
  const navigate = useNavigate();
  const url = `${API_BASE_URL}/${CARS_API_ROUTE}/${id}`;
  const deleteCarReq: () => Promise<void> = async () => {
    const res = await fetch(url, {
      method: "DELETE",
    });
    if (!res.ok) {
      throw new Error("failed to delete car");
    }
  };
  const {
    mutate: deleteCar, isLoading, isError, error,
  } = useMutation(deleteCarReq, {
    onSuccess: () => {
      queryClient.invalidateQueries(GET_CARS);
      toast.success("Car was deleted successfully");
      if (returnTo) {
        navigate(returnTo);
      }
    },
  });
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    deleteCar, isLoading, isError, error,
  };
};
