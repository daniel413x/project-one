import { useQuery } from "react-query";
import { toast } from "sonner";
import qs from "query-string";
import { errorCatch } from "../utils";
import { MAKES_API_ROUTE } from "../consts";
import { MakesGETManyRes } from "../types";

const API_BASE_URL = import.meta.env.VITE_APP_API_URL;

const GET_MAKES = "getMakes";

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
