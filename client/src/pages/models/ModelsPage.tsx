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
import { cn } from "@/lib/utils";
import { useGetModels } from "@/lib/api/ModelsApi";
import usePaginatedQueriesLogic from "@/lib/hooks/usePaginatedQueriesLogic";
import CreateModelButton from "./components/CreateModelButton";
import ModelCard from "./components/ModelCard";

function ModelsPage() {
  const {
    data,
    isLoading: isLoadingGET,
  } = useGetModels();
  const {
    page,
    handleClearSearch,
    handleSetSearch,
    handleSetPage,
    search,
  } = usePaginatedQueriesLogic();
  return (
    <Meta title="Models">
      <main>
        <PageHeader header={`Models ${data?.pagination.count ? `(${data?.pagination.count})` : ""}`} icon={<ComputerIcon />} />
        <ContentFrame mt>
          <div className="relative">
            <MagnifyingGlassIcon
              className="absolute top-1/2 -translate-y-1/2 left-4 scale-150"
            />
            <Input
              className="ps-12"
              placeholder="Search by model name"
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
              <XIcon
                aria-hidden="true"
              />
            </button>
          </div>
          <ul className={cn("grid sm:grid-cols-2 lg:grid-cols-3 gap-5 mt-4 min-h-screen", {
            "flex justify-center pt-4": isLoadingGET,
          })}
          >
            {isLoadingGET ? <LoadingSpinner /> : <li><CreateModelButton /></li>}
            {data?.rows.map((model) => (
              <li key={model.id}>
                <ModelCard
                  model={model}
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

export default ModelsPage;
