import { useQuery } from "react-query";
import { toast } from "sonner";
import qs from "query-string";
import { errorCatch } from "../utils";
import { MODELS_API_ROUTE } from "../consts";
import { ModelsGETManyRes } from "../types";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const GET_MODELS = "getModels";

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
