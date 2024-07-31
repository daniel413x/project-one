import { useMutation, useQuery } from "react-query";
import { toast } from "sonner";
import qs from "query-string";
import { useNavigate, useSearchParams } from "react-router-dom";
import { MakeFormValues } from "@/pages/create-makes/CreateMakePage";
import { errorCatch } from "../utils";
import { CARS_API_ROUTE, MAKES_API_ROUTE } from "../consts";
import { Make, MakesGETManyRes } from "../types";
import queryClient from "./queryClient";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const GET_MAKES = "getMakes";
const GET_MAKE = "getMake";
const GET_COUNT = "getMakesCarsCount";

export const useGetMakes = () => {
  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get("page")) - 1 || 0;
  const search = searchParams.get("search");
  const sortBy = searchParams.get("sortBy");
  const url = qs.stringifyUrl({
    url: `${API_BASE_URL}/${MAKES_API_ROUTE}`,
    query: {
      page,
      sortBy,
      search,
    },
  }, { skipNull: true });
  const getMakesReq: () => Promise<MakesGETManyRes> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get makes");
    }
    return res.json();
  };
  const {
    data: fetchedMakes, isLoading, isError, error,
    // GET_MAKES must precede url for invalidation to work
  } = useQuery([GET_MAKES, url], getMakesReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedMakes, isLoading, isError, error,
  };
};

export const useGetMake = (id?: string | null) => {
  const url = `${API_BASE_URL}/${MAKES_API_ROUTE}/${id}`;
  const getMakeReq: () => Promise<Make> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error(`Failed to get make with id ${id}`);
    }
    return res.json();
  };
  const {
    data: fetchedMake, isLoading, isError, error,
  } = useQuery([GET_MAKE, url], getMakeReq, {
    enabled: !!id,
  });
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    fetchedMake, isLoading, isError, error,
  };
};

export const useGetMakesForFormField = (search: string | undefined) => {
  const size = 5;
  const url = qs.stringifyUrl({
    url: `${API_BASE_URL}/${MAKES_API_ROUTE}`,
    query: {
      search,
      size,
    },
  }, { skipNull: true });
  const getMakesReq: () => Promise<MakesGETManyRes> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get makes");
    }
    return res.json();
  };
  const {
    data: fetchedMakes, isLoading, isError, error,
  } = useQuery([url, GET_MAKES], getMakesReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedMakes, isLoading, isError, error,
  };
};

export const useGetCarsCount = (name: string | undefined) => {
  const url = `${API_BASE_URL}/${MAKES_API_ROUTE}/${name}/${CARS_API_ROUTE}/count`;
  const getCarsCountReq: () => Promise<number> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get make's cars count");
    }
    return res.json();
  };
  const {
    data: fetchedMakes, isLoading, isError, error,
  } = useQuery([url, GET_COUNT], getCarsCountReq, {
    enabled: !!name,
  });
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedMakes, isLoading, isError, error,
  };
};

export const useCreateMake = () => {
  const createMakeReq = async (values: MakeFormValues): Promise<Make> => {
    const form = {
      ...values,
    };
    const body = JSON.stringify({
      ...form,
    });
    const res = await fetch(`${API_BASE_URL}/${MAKES_API_ROUTE}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body,
    });
    if (!res.ok) {
      throw new Error("failed to create make");
    }
    const json = await res.json();
    return json;
  };
  const {
    mutateAsync: createMake, isLoading, isError, isSuccess,
  } = useMutation(createMakeReq);
  return {
    createMake, isLoading, isError, isSuccess,
  };
};

export const useUpdateMake = (id: string) => {
  const updateMakeReq = async (values: MakeFormValues): Promise<void> => {
    const form = {
      ...values,
    };
    const body = JSON.stringify({
      ...form,
    });
    const res = await fetch(`${API_BASE_URL}/${MAKES_API_ROUTE}/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body,
    });
    if (!res.ok) {
      throw new Error("failed to update make");
    }
  };
  const {
    mutateAsync: updateMake, isLoading, isError, isSuccess,
  } = useMutation(updateMakeReq);
  return {
    updateMake, isLoading, isError, isSuccess,
  };
};

export const useDeleteMake = (id: number, returnTo?: string) => {
  const navigate = useNavigate();
  const url = `${API_BASE_URL}/${MAKES_API_ROUTE}/${id}`;
  const deleteMakeReq: () => Promise<void> = async () => {
    const res = await fetch(url, {
      method: "DELETE",
    });
    if (!res.ok) {
      throw new Error("Failed delete make");
    }
  };
  const {
    mutate: deleteMake, isLoading, isError, error,
  } = useMutation(deleteMakeReq, {
    onSuccess: () => {
      queryClient.invalidateQueries(GET_MAKES);
      toast.success("Make was deleted successfully");
      if (returnTo) {
        navigate(returnTo);
      }
    },
  });
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    deleteMake, isLoading, isError, error,
  };
};
