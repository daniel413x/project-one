import { ReactNode } from "react";
import { Car } from "@/lib/types";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTrigger,
  DialogTitle,
} from "@/components/ui/common/shadcn/dialog";

interface CarInfoDialogProps {
  children: ReactNode;
  car: Car;
}

function CarInfoDialog({
  children,
  car,
}: CarInfoDialogProps) {
  return (
    <Dialog>
      <DialogTrigger asChild>
        {children}
      </DialogTrigger>
      <DialogContent data-testid="owner-info-modal">
        <DialogHeader>
          <DialogTitle>
            Vehicle information
          </DialogTitle>
        </DialogHeader>
        <div className="flex flex-col">
          <span className="uppercase text-xs">
            vin
          </span>
          <span className="">
            {car.vin}
          </span>
        </div>
        <div className="flex flex-col">
          <span className="uppercase text-xs">
            make
          </span>
          <span className="">
            {car.make.name}
          </span>
        </div>
        <div className="flex flex-col">
          <span className="uppercase text-xs">
            model
          </span>
          <span className="">
            {car.model.name}
          </span>
        </div>
      </DialogContent>
    </Dialog>
  );
}

export default CarInfoDialog;
