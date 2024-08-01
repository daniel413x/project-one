import { ReactNode } from "react";
import { Model } from "@/lib/types";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTrigger,
  DialogTitle,
} from "@/components/ui/common/shadcn/dialog";
import { Separator } from "@/components/ui/common/shadcn/separator";
import LoadingSpinner from "@/components/ui/common/LoadingSpinner";

interface ModelInfoDialogProps {
  children: ReactNode;
  model: Model;
  carsCount: number | undefined;
}

function ModelInfoDialog({
  children,
  model,
  carsCount,
}: ModelInfoDialogProps) {
  return (
    <Dialog>
      <DialogTrigger asChild>
        {children}
      </DialogTrigger>
      <DialogContent data-testid="model-info-modal">
        <DialogHeader>
          <DialogTitle>
            Model information
          </DialogTitle>
        </DialogHeader>
        <Separator className="w-1/2 m-auto sm:m-[unset] " />
        <div className="flex flex-col">
          <span className="uppercase text-xs">
            name
          </span>
          <span>
            {model.name}
          </span>
        </div>
        <div className="flex flex-col">
          <span className="uppercase text-xs">
            make
          </span>
          <span>
            {model.makeName}
          </span>
        </div>
        <div className="flex flex-col">
          <span className="uppercase text-xs">
            cars in warehouse
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

export default ModelInfoDialog;
