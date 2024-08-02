import { ReactNode } from "react";
import { Owner } from "@/lib/types";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTrigger,
  DialogTitle,
} from "@/components/ui/common/shadcn/dialog";
import { Separator } from "@/components/ui/common/shadcn/separator";
import LoadingSpinner from "@/components/ui/common/LoadingSpinner";

interface OwnerInfoDialogProps {
  children: ReactNode;
  owner: Owner;
  carsCount: number | undefined;
}

function OwnerInfoDialog({
  children,
  owner,
  carsCount,
}: OwnerInfoDialogProps) {
  return (
    <Dialog>
      <DialogTrigger asChild>
        {children}
      </DialogTrigger>
      <DialogContent data-testid="owner-info-modal">
        <DialogHeader>
          <DialogTitle>
            Owner information
          </DialogTitle>
        </DialogHeader>
        <Separator className="w-1/2 m-auto sm:m-[unset] " />
        <div className="flex flex-col">
          <span className="uppercase text-xs">
            name
          </span>
          <span>
            {owner.name}
          </span>
        </div>
        <div className="flex flex-col">
          <span className="uppercase text-xs">
            cars owned in warehouse
          </span>
          {carsCount === undefined ? <LoadingSpinner /> : (
            <span>
              {`(${carsCount})`}
            </span>
          )}
        </div>
      </DialogContent>
    </Dialog>
  );
}

export default OwnerInfoDialog;
