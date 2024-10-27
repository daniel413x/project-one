import { ReactNode } from "react";
import { Owner } from "@/lib/types";
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
import { useDeleteOwner } from "@/lib/api/OwnersApi";
import { ExclamationTriangleIcon, QuestionMarkCircledIcon } from "@radix-ui/react-icons";
import { toKebabCase } from "@/lib/utils";

interface DeleteOwnerAlertDialogProps {
  children: ReactNode;
  owner: Owner;
  carsCount: number | undefined;
  returnTo?: string;
}

function DeleteOwnerAlertDialog({
  children,
  owner,
  carsCount,
  returnTo,
}: DeleteOwnerAlertDialogProps) {
  const {
    deleteOwner,
  } = useDeleteOwner(owner.id, returnTo);
  const handleClickConfirm = async () => {
    await deleteOwner();
  };
  const addCarsDeletionWarning = carsCount && carsCount > 0;
  return (
    <AlertDialog>
      <AlertDialogTrigger asChild>
        {children}
      </AlertDialogTrigger>
      <AlertDialogContent id={`delete-owner-${toKebabCase(owner.name)}-dialog`} data-testid="owner-info-modal">
        <AlertDialogHeader>
          <AlertDialogTitle>
            Delete owner
          </AlertDialogTitle>
        </AlertDialogHeader>
        <div className="flex gap-1 text-orange-700">
          <QuestionMarkCircledIcon className="w-8 h-8 shrink-0" />
          <span className="font-semibold">
            Are you sure you want to delete the owner
            {" "}
            <span className="italic">
              {owner.name}
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

export default DeleteOwnerAlertDialog;
