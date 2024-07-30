import { ReactNode } from "react";
import { Car } from "@/lib/types";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTrigger,
  DialogTitle,
} from "@/components/ui/common/shadcn/dialog";
import { format } from "date-fns";
import { Separator } from "@/components/ui/common/shadcn/separator";

interface CarInfoDialogProps {
  children: ReactNode;
  car: Car;
}

function CarInfoDialog({
  children,
  car,
}: CarInfoDialogProps) {
  const registrationExpiration = format(new Date(car.registrationExpiration), "MMM d, yyyy");
  const lastMaintenanceDate = format(new Date(car.lastMaintenanceDate), "MMM d, yyyy");
  const insuranceExpiration = format(new Date(car.insuranceExpiration), "MMM d, yyyy");
  return (
    <Dialog>
      <DialogTrigger asChild>
        {children}
      </DialogTrigger>
      <DialogContent data-testid="car-info-modal">
        <DialogHeader>
          <DialogTitle>
            Vehicle information
          </DialogTitle>
        </DialogHeader>
        <Separator className="w-1/2 m-auto sm:m-[unset] " />
        <div className="grid grid-cols-2 gap-6 mt-8">
          <div className="flex flex-col">
            <span className="uppercase text-xs">
              vin
            </span>
            <span>
              {car.vin}
            </span>
          </div>
          <div className="w-full h-full bg-black/5" />
          <div className="flex flex-col">
            <span className="uppercase text-xs">
              make
            </span>
            <span>
              {car.make.name}
            </span>
          </div>
          <div className="flex flex-col">
            <span className="uppercase text-xs">
              model
            </span>
            <span>
              {car.model.name}
            </span>
          </div>
          <div className="flex flex-col">
            <span className="uppercase text-xs">
              year
            </span>
            <span>
              {car.year}
            </span>
          </div>
          <div className="flex flex-col">
            <span className="uppercase text-xs">
              mileage
            </span>
            <span>
              {car.mileage}
            </span>
          </div>
          <div className="flex flex-col">
            <span className="uppercase text-xs">
              price (usd)
            </span>
            <span>
              {car.price}
            </span>
          </div>
          <div className="w-full h-full bg-black/5" />
          <div className="flex flex-col">
            <span className="uppercase text-xs">
              registration no.
            </span>
            <span>
              {car.registrationNumber}
            </span>
          </div>
          <div className="flex flex-col">
            <span className="uppercase text-xs">
              registration exp.
            </span>
            <span>
              {registrationExpiration}
            </span>
          </div>
          <div className="flex flex-col">
            <span className="uppercase text-xs">
              insurance policy no.
            </span>
            <span>
              {car.insurancePolicyNumber}
            </span>
          </div>
          <div className="flex flex-col">
            <span className="uppercase text-xs">
              insurance exp.
            </span>
            <span>
              {insuranceExpiration}
            </span>
          </div>
          <div className="flex flex-col">
            <span className="uppercase text-xs">
              last maintenance date
            </span>
            <span>
              {lastMaintenanceDate}
            </span>
          </div>
        </div>
      </DialogContent>
    </Dialog>
  );
}

export default CarInfoDialog;
