import { Button } from "@/components/ui/common/shadcn/button";
import { Info, User, Wrench } from "lucide-react";
import { Car } from "@/lib/types";
import carPlaceholder from "@/assets/car.png";
import { Skeleton } from "@/components/ui/common/shadcn/skeleton";
import OwnerInfoDialog from "./OwnerInfoDialog";
import CarInfoDialog from "./CarInfoDialog";

interface CarCardProps {
  car: Car;
}

function CarCard({
  car,
}: CarCardProps) {
  return (
    <div className="flex flex-col py-2 px-4 border">
      <img
        src={carPlaceholder}
        alt=""
        className="w-full"
      />
      <div className="flex flex-col py-4">
        <span className="break-all sm:break-normal sm:truncate">
          {`${car.make.name} ${car.model.name} ${car.vin}`}
        </span>
        <span className="font-bold">
          Owner
        </span>
        <span className="truncate">
          {car.owner.name}
        </span>
        <span className="font-bold">
          Contact
        </span>
        <span className="truncate">
          {car.owner.contact}
        </span>
      </div>
      <div className="grid grid-cols-3 gap-4">
        <Button variant="outline">
          <Wrench />
        </Button>
        <OwnerInfoDialog owner={car.owner}>
          <Button variant="outline">
            <User />
          </Button>
        </OwnerInfoDialog>
        <CarInfoDialog car={car}>
          <Button variant="outline">
            <Info />
          </Button>
        </CarInfoDialog>
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
