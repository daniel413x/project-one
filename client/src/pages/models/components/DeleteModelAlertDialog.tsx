import { ReactNode } from "react";
import { Model } from "@/lib/types";
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
import { useDeleteModel } from "@/lib/api/ModelsApi";
import { ExclamationTriangleIcon, QuestionMarkCircledIcon } from "@radix-ui/react-icons";

interface DeleteModelAlertDialogProps {
  children: ReactNode;
  model: Model;
  carsCount: number | undefined;
  returnTo?: string;
}

function DeleteModelAlertDialog({
  children,
  model,
  carsCount,
  returnTo,
}: DeleteModelAlertDialogProps) {
  const {
    deleteModel,
  } = useDeleteModel(model.id, returnTo);
  const handleClickConfirm = async () => {
    await deleteModel();
  };
  const addCarsDeletionWarning = carsCount && carsCount > 0;
  return (
    <AlertDialog>
      <AlertDialogTrigger asChild>
        {children}
      </AlertDialogTrigger>
      <AlertDialogContent data-testid="model-info-modal">
        <AlertDialogHeader>
          <AlertDialogTitle>
            Delete model
          </AlertDialogTitle>
        </AlertDialogHeader>
        <div className="flex gap-1 text-orange-700">
          <QuestionMarkCircledIcon className="w-8 h-8 shrink-0" />
          <span className="font-semibold">
            Are you sure you want to delete the model
            {" "}
            <span className="italic">
              {model.name}
            </span>
            ?
          </span>
        </div>
        <div className="flex gap-1 text-orange-700">
          <ExclamationTriangleIcon className="w-8 h-8" />
          This is an irreversible action
        </div>
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

export default DeleteModelAlertDialog;
