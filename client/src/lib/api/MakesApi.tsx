import { useQuery } from "react-query";
import { toast } from "sonner";
import qs from "query-string";
import { useSearchParams } from "react-router-dom";
import { errorCatch } from "../utils";
import { CARS_API_ROUTE, MAKES_API_ROUTE } from "../consts";
import { MakesGETManyRes } from "../types";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const GET_MAKES = "getMakes";
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
  } = useQuery([url, GET_MAKES], getMakesReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedMakes, isLoading, isError, error,
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
  const getMakeReq: () => Promise<MakesGETManyRes> = async () => {
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
  } = useQuery([url, GET_MAKES], getMakeReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedMakes, isLoading, isError, error,
  };
};

export const useGetCarsCount = (name: string) => {
  const url = `${API_BASE_URL}/${MAKES_API_ROUTE}/${name}/${CARS_API_ROUTE}/count`;
  const getMakeReq: () => Promise<number> = async () => {
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
  } = useQuery([url, GET_COUNT], getMakeReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedMakes, isLoading, isError, error,
  };
};
