import { useQuery } from "react-query";
import { toast } from "sonner";
import qs from "query-string";
import { errorCatch } from "../utils";
import { CARS_API_ROUTE, MODELS_API_ROUTE } from "../consts";
import { ModelsGETManyRes } from "../types";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const GET_MODELS = "getModels";
const GET_COUNT = "getModelsCarsCount";

export const useGetModelsForFormField = (search: string | undefined) => {
  const size = 5;
  const url = qs.stringifyUrl({
    url: `${API_BASE_URL}/${MODELS_API_ROUTE}`,
    query: {
      search,
      size,
    },
  }, { skipNull: true });
  const getModelReq: () => Promise<ModelsGETManyRes> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get models");
    }
    return res.json();
  };
  const {
    data: fetchedModels, isLoading, isError, error,
  } = useQuery([url, GET_MODELS], getModelReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedModels, isLoading, isError, error,
  };
};

export const useGetCarsCount = (name: string) => {
  const url = `${API_BASE_URL}/${MODELS_API_ROUTE}/${name}/${CARS_API_ROUTE}/count`;
  const getMakeReq: () => Promise<number> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get model's cars count");
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
