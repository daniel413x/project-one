import { Button } from "@/components/ui/common/shadcn/button";
import {
  Info, Trash2, User, Wrench,
} from "lucide-react";
import { Car } from "@/lib/types";
import { Skeleton } from "@/components/ui/common/shadcn/skeleton";
import { useNavigate } from "react-router-dom";
import { CARS_ROUTE, CAR_PLACEHOLDER_IMG_URL } from "@/lib/consts";
import OwnerInfoDialog from "./OwnerInfoDialog";
import CarInfoDialog from "./CarInfoDialog";
import DeleteCarDialog from "./DeleteCarDialog";

interface CarCardProps {
  car: Car;
}

function CarCard({
  car,
}: CarCardProps) {
  const navigate = useNavigate();
  const navigateToCarEditPage = () => {
    navigate(`/${CARS_ROUTE}/${car.id}`);
  };
  return (
    <div className="flex flex-col py-2 px-4 border">
      <img
        src={CAR_PLACEHOLDER_IMG_URL}
        alt=""
        className="w-full"
      />
      <div className="flex flex-col py-4">
        <span className="flex break-all sm:break-normal sm:truncate mb-1">
          <img
            src={car.make.logoUrl}
            alt=""
            className="w-[24px] mr-1"
          />
          {`${car.make.name} ${car.model.name} ${car.vin}`}
        </span>
        <span className="font-bold">
          Owner
        </span>
        <span className="truncate">
          {car.owner?.name || "N/A"}
        </span>
        <span className="font-bold">
          Contact
        </span>
        <span className="truncate">
          {car.owner?.contact || "N/A"}
        </span>
      </div>
      <div className="grid sm:grid-cols-4 gap-4">
        <Button
          variant="outline"
          onClick={navigateToCarEditPage}
        >
          <Wrench className="text-stone-700 shrink-0" />
        </Button>
        <CarInfoDialog car={car}>
          <Button variant="outline">
            <Info className="text-stone-700 shrink-0" />
          </Button>
        </CarInfoDialog>
        <OwnerInfoDialog owner={car.owner}>
          <Button
            variant="outline"
          >
            <User className="text-stone-700 shrink-0" />
          </Button>
        </OwnerInfoDialog>
        <DeleteCarDialog car={car}>
          <Button
            variant="outline"
          >
            <Trash2 className="text-red-700 shrink-0" />
          </Button>
        </DeleteCarDialog>
      </div>
    </div>
  );
}

export function CarCardSkeleton() {
  return (
    <Skeleton className="w-full h-full" />
  );
}

export default CarCard;
