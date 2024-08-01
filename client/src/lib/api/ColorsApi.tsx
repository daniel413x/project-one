import { useMutation, useQuery } from "react-query";
import { toast } from "sonner";
import qs from "query-string";
import { useSearchParams } from "react-router-dom";
import { errorCatch } from "../utils";
import { COLORS_API_ROUTE } from "../consts";
import { ColorsGETManyRes } from "../types";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const GET_COLORS = "getColors";

export const useGetColors = () => {
  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get("page")) - 1 || 0;
  const search = searchParams.get("search");
  const size = 5;
  const url = qs.stringifyUrl({
    url: `${API_BASE_URL}/${COLORS_API_ROUTE}`,
    query: {
      page,
      search,
      size,
    },
  }, { skipNull: true });
  const getColorReq: () => Promise<ColorsGETManyRes> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get colors");
    }
    return res.json();
  };
  const {
    data: fetchedColors, isLoading, isError, error,
  } = useQuery([url, GET_COLORS], getColorReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedColors, isLoading, isError, error,
  };
};

export const useGetColorsForFormField = (search: string | undefined) => {
  const size = 5;
  const url = qs.stringifyUrl({
    url: `${API_BASE_URL}/${COLORS_API_ROUTE}`,
    query: {
      search,
      size,
    },
  }, { skipNull: true });
  const getColorReq: () => Promise<ColorsGETManyRes> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get colors");
    }
    return res.json();
  };
  const {
    data: fetchedColors, isLoading, isError, error,
  } = useQuery([url, GET_COLORS], getColorReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedColors, isLoading, isError, error,
  };
};

export type CreateColorForm = {
  name: string;
};

export const useCreateColor = () => {
  const createColorReq = async (data: CreateColorForm) => {
    const res = await fetch(`${API_BASE_URL}/${COLORS_API_ROUTE}`, {
      method: "POST",
      body: JSON.stringify(data),
    });
    if (!res.ok) {
      throw new Error("Failed to create color");
    }
  };
  const {
    mutateAsync: createColor, isLoading, isError, isSuccess,
  } = useMutation(createColorReq);
  return {
    createColor, isLoading, isError, isSuccess,
  };
};
