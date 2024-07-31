import { ReactNode } from "react";
import { Make } from "@/lib/types";
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
import { useDeleteMake } from "@/lib/api/MakesApi";
import { ExclamationTriangleIcon, QuestionMarkCircledIcon } from "@radix-ui/react-icons";

interface DeleteMakeAlertDialogProps {
  children: ReactNode;
  make: Make;
  carsCount: number | undefined;
}

function DeleteMakeAlertDialog({
  children,
  make,
  carsCount,
}: DeleteMakeAlertDialogProps) {
  const {
    deleteMake,
  } = useDeleteMake(make.id);
  const handleClickConfirm = async () => {
    await deleteMake();
  };
  const addCarsDeletionWarning = carsCount && carsCount > 0;
  const addModelsDeletionWarning = make.models.length > 0;
  return (
    <AlertDialog>
      <AlertDialogTrigger asChild>
        {children}
      </AlertDialogTrigger>
      <AlertDialogContent data-testid="make-info-modal">
        <AlertDialogHeader>
          <AlertDialogTitle>
            Delete make
          </AlertDialogTitle>
        </AlertDialogHeader>
        <div className="flex gap-1 text-orange-700">
          <QuestionMarkCircledIcon className="w-8 h-8 shrink-0" />
          <span className="font-semibold">
            Are you sure you want to delete the make
            {" "}
            <span className="italic">
              {make.name}
            </span>
            ?
          </span>
        </div>
        <div className="flex gap-1 text-orange-700">
          <ExclamationTriangleIcon className="w-8 h-8" />
          This is an irreversible action
        </div>
        {!addModelsDeletionWarning ? null : (
          <div className="flex  gap-1 text-orange-700">
            <ExclamationTriangleIcon className="w-8 h-8" />
            <span>
              All associated models
              <span className="mx-1 font-bold">
                {`(${make.models.length})`}
              </span>
              will be deleted
            </span>
          </div>
        )}
        {!addCarsDeletionWarning ? null : (
          <div className="flex  gap-1 text-orange-700">
            <ExclamationTriangleIcon className="w-8 h-8" />
            <span>
              All associated cars
              <span className="mx-1 font-bold">
                {`(${carsCount})`}
              </span>
              will be deleted
            </span>
          </div>
        )}
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

export default DeleteMakeAlertDialog;
