import { ReactNode } from "react";
import { Make, Model } from "@/lib/types";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTrigger,
  DialogTitle,
} from "@/components/ui/common/shadcn/dialog";
import { Separator } from "@/components/ui/common/shadcn/separator";
import { useGetCarsCount } from "@/lib/api/ModelsApi";
import LoadingSpinner from "@/components/ui/common/LoadingSpinner";

interface ModelInfoProps {
  model: Model;
}

function ModelInfo({
  model,
}: ModelInfoProps) {
  const {
    data: totalCars,
    isLoading: isLoadingGetCarsCount,
  } = useGetCarsCount(model.name);
  return (
    <div className="grid grid-cols-2">
      <span className="italic">
        {model.name}
      </span>
      {isLoadingGetCarsCount ? <LoadingSpinner /> : (
        <span>
          {`(${totalCars})`}
        </span>
      )}
    </div>
  );
}

interface MakeInfoDialogProps {
  children: ReactNode;
  make: Make;
}

function MakeInfoDialog({
  children,
  make,
}: MakeInfoDialogProps) {
  return (
    <Dialog>
      <DialogTrigger asChild>
        {children}
      </DialogTrigger>
      <DialogContent data-testid="make-info-modal">
        <DialogHeader>
          <DialogTitle>
            Make information
          </DialogTitle>
        </DialogHeader>
        <Separator className="w-1/2 m-auto sm:m-[unset] " />
        <div className="flex flex-col">
          <span className="uppercase text-xs">
            name
          </span>
          <span>
            {make.name}
          </span>
        </div>
        <div className="flex flex-col mt-3">
          <span className="uppercase text-xs mb-2">
            models
          </span>
          <div className="grid grid-cols-2">
            <span className="uppercase text-xs">
              name
            </span>
            <span className="uppercase text-xs">
              in warehouse
            </span>
          </div>
          {/* TODO: add pagination */}
          <ul className="">
            {make.models.map((model) => (
              <li key={model.id}>
                <ModelInfo model={model} />
              </li>
            ))}
          </ul>
        </div>
      </DialogContent>
    </Dialog>
  );
}

export default MakeInfoDialog;
