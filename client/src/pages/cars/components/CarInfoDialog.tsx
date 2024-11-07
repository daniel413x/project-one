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
import ModalKeyValuePair from "@/components/ui/common/ModalKeyValuePair";

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
        <ul className="grid grid-cols-2 gap-6 mt-8">
          <li>
            <ModalKeyValuePair k="vin" value={car.vin} />
          </li>
          <div className="w-full h-full bg-black/5" />
          <li>
            <ModalKeyValuePair k="make" value={car.make.name} />
          </li>
          <li>
            <ModalKeyValuePair k="model" value={car.model.name} />
          </li>
          <li>
            <ModalKeyValuePair k="year" value={car.year} />
          </li>
          <li>
            <ModalKeyValuePair k="mileage" value={car.mileage} />
          </li>
          <li>
            <ModalKeyValuePair k="price (USD)" value={car.price} />
          </li>
          <div className="w-full h-full bg-black/5" />
          <li>
            <ModalKeyValuePair k="registration no." ariaLabel="registration number" value={car.registrationNumber} />
          </li>
          <li>
            <ModalKeyValuePair k="registration exp." ariaLabel="registration expiration" value={registrationExpiration} />
          </li>
          <li>
            <ModalKeyValuePair k="insurance policy no." ariaLabel="insurance policy number" value={car.insurancePolicyNumber} />
          </li>
          <li>
            <ModalKeyValuePair k="insurance exp." value={insuranceExpiration} />
          </li>
          <li>
            <ModalKeyValuePair k="last maintenance date" value={lastMaintenanceDate} />
          </li>
        </ul>
      </DialogContent>
    </Dialog>
  );
}

export default CarInfoDialog;
