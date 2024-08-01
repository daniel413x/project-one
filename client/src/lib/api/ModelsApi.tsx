import { useMutation, useQuery } from "react-query";
import { toast } from "sonner";
import qs from "query-string";
import { useNavigate, useSearchParams } from "react-router-dom";
import { ModelFormValues } from "@/pages/models/routes/create-models/CreateModelPage";
import { errorCatch } from "../utils";
import { CARS_API_ROUTE, MODELS_API_ROUTE } from "../consts";
import { Model, ModelsGETManyRes } from "../types";
import queryClient from "./queryClient";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const GET_MODELS = "getModels";
const GET_MODEL = "getModel";
const GET_COUNT = "getModelsCarsCount";

export const useGetModels = () => {
  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get("page")) - 1 || 0;
  const search = searchParams.get("search");
  const sortBy = searchParams.get("sortBy");
  const url = qs.stringifyUrl({
    url: `${API_BASE_URL}/${MODELS_API_ROUTE}`,
    query: {
      page,
      sortBy,
      search,
    },
  }, { skipNull: true });
  const getModelsReq: () => Promise<ModelsGETManyRes> = async () => {
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
    // GET_MODELS must precede url for invalidation to work
  } = useQuery([GET_MODELS, url], getModelsReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedModels, isLoading, isError, error,
  };
};

export const useGetModel = (id?: string | null) => {
  const url = `${API_BASE_URL}/${MODELS_API_ROUTE}/${id}`;
  const getModelReq: () => Promise<Model> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error(`Failed to get model with id ${id}`);
    }
    return res.json();
  };
  const {
    data: fetchedModel, isLoading, isError, error,
  } = useQuery([GET_MODEL, url], getModelReq, {
    enabled: !!id,
  });
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    fetchedModel, isLoading, isError, error,
  };
};

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

export const useGetCarsCount = (name?: string) => {
  const url = `${API_BASE_URL}/${MODELS_API_ROUTE}/${name}/${CARS_API_ROUTE}/count`;
  const getModelReq: () => Promise<number> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get model's cars count");
    }
    return res.json();
  };
  const {
    data: fetchedModels, isLoading, isError, error,
  } = useQuery([url, GET_COUNT], getModelReq, {
    enabled: !!name,
  });
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedModels, isLoading, isError, error,
  };
};

export const useCreateModel = () => {
  const createModelReq = async (data: ModelFormValues): Promise<Model> => {
    const form = JSON.stringify({
      ...data,
      makeId: data.make.id,
    });
    const res = await fetch(`${API_BASE_URL}/${MODELS_API_ROUTE}`, {
      method: "POST",
      body: form,
      headers: {
        "Content-Type": "application/json",
      },
    });
    if (!res.ok) {
      throw new Error("Failed to create model");
    }
    const json = await res.json();
    return json;
  };
  const {
    mutateAsync: createModel, isLoading, isError, isSuccess,
  } = useMutation(createModelReq);
  return {
    createModel, isLoading, isError, isSuccess,
  };
};

export const useUpdateModel = (id: string) => {
  const updateModelReq = async (data: ModelFormValues): Promise<void> => {
    const body = JSON.stringify({
      ...data,
      makeId: data.make.id,
    });
    const res = await fetch(`${API_BASE_URL}/${MODELS_API_ROUTE}/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body,
    });
    if (!res.ok) {
      throw new Error("failed to update model");
    }
  };
  const {
    mutateAsync: updateModel, isLoading, isError, isSuccess,
  } = useMutation(updateModelReq);
  return {
    updateModel, isLoading, isError, isSuccess,
  };
};

export const useDeleteModel = (id: number, returnTo?: string) => {
  const navigate = useNavigate();
  const url = `${API_BASE_URL}/${MODELS_API_ROUTE}/${id}`;
  const deleteModelReq: () => Promise<void> = async () => {
    const res = await fetch(url, {
      method: "DELETE",
    });
    if (!res.ok) {
      throw new Error("Failed to delete model");
    }
  };
  const {
    mutate: deleteModel, isLoading, isError, error,
  } = useMutation(deleteModelReq, {
    onSuccess: () => {
      queryClient.invalidateQueries(GET_MODELS);
      toast.success("Model was deleted successfully");
      if (returnTo) {
        navigate(returnTo);
      }
    },
  });
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    deleteModel, isLoading, isError, error,
  };
};
