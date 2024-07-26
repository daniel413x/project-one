import {
  ComputerIcon,
} from "lucide-react";
import Meta from "@/components/misc/Meta";
import PageHeader from "@/components/ui/common/PageHeader";
import { useGetCars } from "@/lib/api/CarsApi";
import ContentFrame from "@/components/ui/common/ContentFrame";
import { MagnifyingGlassIcon } from "@radix-ui/react-icons";
import { Input } from "@/components/ui/common/shadcn/input";
import LoadingSpinner from "@/components/ui/common/LoadingSpinner";
import CarCard from "./components/CarCard";

function CarsPage() {
  const {
    data,
    isLoading: isLoadingGET,
  } = useGetCars();
  return (
    <Meta title="Cars">
      <main>
        <PageHeader header="Cars" isLoading={isLoadingGET} icon={<ComputerIcon />} />
        <ContentFrame mt>
          <div className="relative">
            <MagnifyingGlassIcon
              className="absolute top-1/2 -translate-y-1/2 left-4 scale-150 "
            />
            <Input
              className="ps-12"
              placeholder="Search by owner name"
            />
          </div>
          <ul className="grid sm:grid-cols-2 lg:grid-cols-3 gap-5 mt-4">
            {isLoadingGET ? <LoadingSpinner /> : null}
            {data?.rows.map((car) => (
              <li key={car.id}>
                <CarCard car={car} />
              </li>
            ))}
          </ul>
        </ContentFrame>
      </main>
    </Meta>
  );
}

export default CarsPage;
