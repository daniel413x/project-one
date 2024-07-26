import { useQuery } from "react-query";
import { toast } from "sonner";
import { errorCatch } from "../utils";
import { CARS_ROUTE } from "../consts";
import { CarsGETManyRes } from "../types";
import qs from "query-string";
import { useSearchParams } from "react-router-dom";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const GET_CARS = "getCars";

export const useGetCars = () => {
  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get("page")) - 1 || 0;
  const search = searchParams.get("search");
  const sortBy = searchParams.get("sortBy");
  const url = qs.stringifyUrl({
    url: `${API_BASE_URL}/${CARS_ROUTE}`,
    query: {
      page,
      sortBy,
      search,
    },
  }, { skipNull: true });
  const getMyOrderReq: () => Promise<CarsGETManyRes> = async () => {
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
  } = useQuery([url, GET_CARS], getMyOrderReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedCars, isLoading, isError, error,
  };
};
