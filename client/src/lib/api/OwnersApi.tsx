import { useQuery } from "react-query";
import { toast } from "sonner";
import qs from "query-string";
import { errorCatch } from "../utils";
import { OWNERS_API_ROUTE } from "../consts";
import { OwnersGETManyRes } from "../types";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const GET_OWNERS = "getOwners";

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
