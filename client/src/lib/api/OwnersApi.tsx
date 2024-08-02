import { useMutation, useQuery } from "react-query";
import { toast } from "sonner";
import qs from "query-string";
import { useNavigate, useSearchParams } from "react-router-dom";
import { OwnerFormValues } from "@/pages/owners/routes/create-owners/CreateOwnerPage";
import { errorCatch } from "../utils";
import { Owner, OwnersGETManyRes } from "../types";
import queryClient from "./queryClient";
import { CARS_API_ROUTE, OWNERS_API_ROUTE } from "../consts";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const GET_OWNERS = "getOwners";
const GET_OWNER = "getOwner";
const GET_COUNT = "getCarsCountOwners";

export const useGetOwners = () => {
  const [searchParams] = useSearchParams();
  const page = Number(searchParams.get("page")) - 1 || 0;
  const search = searchParams.get("search");
  const sortBy = searchParams.get("sortBy");
  const url = qs.stringifyUrl({
    url: `${API_BASE_URL}/${OWNERS_API_ROUTE}`,
    query: {
      page,
      sortBy,
      search,
    },
  }, { skipNull: true });
  const getOwnersReq: () => Promise<OwnersGETManyRes> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get models");
    }
    return res.json();
  };
  const {
    data: fetchedOwners, isLoading, isError, error,
    // GET_OWNERS must precede url for invalidation to work
  } = useQuery([GET_OWNERS, url], getOwnersReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedOwners, isLoading, isError, error,
  };
};

export const useGetOwnersForFormField = (search: string | undefined) => {
  const size = 5;
  const url = qs.stringifyUrl({
    url: `${API_BASE_URL}/${OWNERS_API_ROUTE}`,
    query: {
      search,
      size,
    },
  }, { skipNull: true });
  const getOwnerReq: () => Promise<OwnersGETManyRes> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get owners");
    }
    return res.json();
  };
  const {
    data: fetchedOwners, isLoading, isError, error,
  } = useQuery([url, GET_OWNERS], getOwnerReq);
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedOwners, isLoading, isError, error,
  };
};

export const useGetOwner = (id?: string | null) => {
  const url = `${API_BASE_URL}/${OWNERS_API_ROUTE}/${id}`;
  const getOwnerReq: () => Promise<Owner> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error(`Failed to get make with id ${id}`);
    }
    return res.json();
  };
  const {
    data: fetchedOwner, isLoading, isError, error,
  } = useQuery([GET_OWNER, url], getOwnerReq, {
    enabled: !!id,
  });
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    fetchedOwner, isLoading, isError, error,
  };
};

export const useGetCarsCount = (id: number | undefined) => {
  const url = `${API_BASE_URL}/${OWNERS_API_ROUTE}/${id}/${CARS_API_ROUTE}/count`;
  const getCarsCountReq: () => Promise<number> = async () => {
    const res = await fetch(url, {
      method: "GET",
    });
    if (!res.ok) {
      throw new Error("Failed to get owner's cars count");
    }
    return res.json();
  };
  const {
    data: fetchedOwners, isLoading, isError, error,
  } = useQuery([url, GET_COUNT], getCarsCountReq, {
    enabled: !!id,
  });
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    data: fetchedOwners, isLoading, isError, error,
  };
};

export const useCreateOwner = () => {
  const createOwnerReq = async (values: OwnerFormValues): Promise<Owner> => {
    const body = JSON.stringify({
      ...values,
    });
    const res = await fetch(`${API_BASE_URL}/${OWNERS_API_ROUTE}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body,
    });
    if (!res.ok) {
      throw new Error("failed to create owner");
    }
    const json = await res.json();
    return json;
  };
  const {
    mutateAsync: createOwner, isLoading, isError, isSuccess,
  } = useMutation(createOwnerReq);
  return {
    createOwner, isLoading, isError, isSuccess,
  };
};

export const useUpdateOwner = (id: string) => {
  const updateOwnerReq = async (data: OwnerFormValues): Promise<void> => {
    const body = JSON.stringify({
      ...data,
    });
    const res = await fetch(`${API_BASE_URL}/${OWNERS_API_ROUTE}/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body,
    });
    if (!res.ok) {
      throw new Error("failed to update owner");
    }
  };
  const {
    mutateAsync: updateOwner, isLoading, isError, isSuccess,
  } = useMutation(updateOwnerReq);
  return {
    updateOwner, isLoading, isError, isSuccess,
  };
};

export const useDeleteOwner = (id: number, returnTo?: string) => {
  const navigate = useNavigate();
  const url = `${API_BASE_URL}/${OWNERS_API_ROUTE}/${id}`;
  const deleteOwnerReq: () => Promise<void> = async () => {
    const res = await fetch(url, {
      method: "DELETE",
    });
    if (!res.ok) {
      throw new Error("Failed to delete model");
    }
  };
  const {
    mutate: deleteOwner, isLoading, isError, error,
  } = useMutation(deleteOwnerReq, {
    onSuccess: () => {
      queryClient.invalidateQueries(GET_OWNERS);
      toast.success("Owner was deleted successfully");
      if (returnTo) {
        navigate(returnTo);
      }
    },
  });
  if (error) {
    toast.error(errorCatch(error));
  }
  return {
    deleteOwner, isLoading, isError, error,
  };
};
