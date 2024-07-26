import { useQuery } from "react-query";
import { toast } from "sonner";
import { errorCatch } from "../utils";
import { CARS_ROUTE } from "../consts";
import { CarsGETManyRes } from "../types";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const GET_CARS = "getCars";

export const useGetCars = () => {
  const getMyOrderReq: () => Promise<CarsGETManyRes> = async () => {
    const res = await fetch(`${API_BASE_URL}/${CARS_ROUTE}`, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get cars");
    }
    return res.json();
  };
  const {
    data: fetchedCars, isLoading, isError, error,
  } = useQuery(GET_CARS, getMyOrderReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedCars, isLoading, isError, error,
  };
};
