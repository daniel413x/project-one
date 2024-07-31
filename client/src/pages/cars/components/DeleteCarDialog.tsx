import { ReactNode } from "react";
import { Car } from "@/lib/types";
import {
  AlertDialog,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogTrigger,
  AlertDialogTitle,
  AlertDialogFooter,
  AlertDialogCancel,
  AlertDialogAction,
} from "@/components/ui/common/shadcn/alert-dialog";
import { useDeleteCar } from "@/lib/api/CarsApi";
import { ExclamationTriangleIcon, QuestionMarkCircledIcon } from "@radix-ui/react-icons";

interface DeleteCarDialogProps {
  children: ReactNode;
  car: Car;
  returnTo?: string;
}

function DeleteCarDialog({
  children,
  car,
  returnTo,
}: DeleteCarDialogProps) {
  const {
    deleteCar,
  } = useDeleteCar(car.id, returnTo);
  const handleClickConfirm = async () => {
    await deleteCar();
  };
  return (
    <AlertDialog>
      <AlertDialogTrigger asChild>
        {children}
      </AlertDialogTrigger>
      <AlertDialogContent data-testid="car-info-modal">
        <AlertDialogHeader>
          <AlertDialogTitle>
            Delete car
          </AlertDialogTitle>
        </AlertDialogHeader>
        <div className="flex gap-1 text-orange-700">
          <QuestionMarkCircledIcon className="w-8 h-8 shrink-0" />
          <span className="font-semibold">
            Are you sure you want to delete the car
            {" "}
            <span className="italic text-sm">
              {`${car.make.name} ${car.model.name} ${car.vin}`}
            </span>
            ?
          </span>
        </div>
        <div className="flex gap-1 text-orange-700">
          <ExclamationTriangleIcon className="w-8 h-8" />
          This is an irreversible action
        </div>
        <AlertDialogFooter>
          <AlertDialogCancel>Cancel</AlertDialogCancel>
          <AlertDialogAction
            onClick={handleClickConfirm}
          >
            Delete
          </AlertDialogAction>
        </AlertDialogFooter>
      </AlertDialogContent>
    </AlertDialog>
  );
}

export default DeleteCarDialog;
