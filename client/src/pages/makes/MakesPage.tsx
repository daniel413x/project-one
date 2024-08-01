import {
  ComputerIcon,
  XIcon,
} from "lucide-react";
import Meta from "@/components/misc/Meta";
import PageHeader from "@/components/ui/common/PageHeader";
import ContentFrame from "@/components/ui/common/ContentFrame";
import { MagnifyingGlassIcon } from "@radix-ui/react-icons";
import { Input } from "@/components/ui/common/shadcn/input";
import LoadingSpinner from "@/components/ui/common/LoadingSpinner";
import PageControl from "@/components/ui/common/PageControl";
import { useSearchParams } from "react-router-dom";
import useDebounce from "@/lib/hooks/useDebounce";
import { ChangeEvent, useState } from "react";
import { cn } from "@/lib/utils";
import { useGetMakes } from "@/lib/api/MakesApi";
import CreateMakeButton from "./components/CreateMakeButton";
import MakeCard from "./components/MakeCard";

function MakesPage() {
  const {
    data,
    isLoading: isLoadingGET,
  } = useGetMakes();
  const [searchParams, setSearchParams] = useSearchParams();
  const page = Number(searchParams.get("page")) || 1;
  const searchedName = searchParams.get("search") || "";
  const handleSetSearchParams = (query: Record<string, any>) => {
    const url = new URLSearchParams(window.location.search);
    const k = Object.keys(query);
    for (let i = 0; i < k.length; i += 1) {
      const prop = k[i];
      if (query[prop] === "") {
        url.delete(prop);
      } else {
        url.set(prop, query[prop]!);
      }
    }
    setSearchParams(url);
  };
  const handleSetPage = (num: number) => {
    window.scrollTo({
      top: 0,
    });
    handleSetSearchParams({
      page: num,
      search: searchedName,
    });
  };
  // will search by make name
  const [search, setSearch] = useState<string>("");
  const handleSetSearch = (e: ChangeEvent<HTMLInputElement>) => {
    setSearch(e.target?.value);
  };
  const handleClearSearch = () => {
    setSearch("");
  };
  const handleSetDebouncedSearch = () => {
    handleSetSearchParams({
      search,
      page: "",
    });
  };
  useDebounce(search, 500, handleSetDebouncedSearch);
  return (
    <Meta title="Makes">
      <main>
        <PageHeader header={`Makes (${data?.pagination.count})`} icon={<ComputerIcon />} />
        <ContentFrame mt>
          <div className="relative">
            <MagnifyingGlassIcon
              className="absolute top-1/2 -translate-y-1/2 left-4 scale-150"
            />
            <Input
              className="ps-12"
              placeholder="Search by make name"
              onChange={handleSetSearch}
              value={search}
            />
            <button
              className={cn("absolute top-1/2 -translate-y-1/2 right-0 p-4 text-red-500", {
                "opacity-50 pointer-events-none": !Input,
              })}
              onClick={handleClearSearch}
              aria-label="Clear input"
              type="button"
            >
              <XIcon />
            </button>
          </div>
          <ul className={cn("grid sm:grid-cols-2 lg:grid-cols-3 gap-5 mt-4 min-h-screen", {
            "flex justify-center pt-4": isLoadingGET,
          })}
          >
            {isLoadingGET ? <LoadingSpinner /> : <CreateMakeButton />}
            {data?.rows.map((make) => (
              <li key={make.id}>
                <MakeCard
                  make={make}
                />
              </li>
            ))}
          </ul>
          <PageControl
            page={page}
            pages={data?.pagination.pages}
            pageLimitReached={data?.pagination.pageLimitReached}
            handleSetPage={handleSetPage}
          />
        </ContentFrame>
      </main>
    </Meta>
  );
}

export default MakesPage;
