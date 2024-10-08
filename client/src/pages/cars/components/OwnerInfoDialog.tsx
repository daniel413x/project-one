import { ReactNode } from "react";
import { Owner } from "@/lib/types";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTrigger,
  DialogTitle,
} from "@/components/ui/common/shadcn/dialog";

interface OwnerInfoDialogProps {
  children: ReactNode;
  owner?: Owner;
}

function OwnerInfoDialog({
  children,
  owner,
}: OwnerInfoDialogProps) {
  return (
    <Dialog>
      <DialogTrigger asChild>
        {children}
      </DialogTrigger>
      <DialogContent data-testid="owner-info-modal">
        <DialogHeader>
          <DialogTitle>
            Vehicle owner information
          </DialogTitle>
        </DialogHeader>
        <div className="flex flex-col">
          <span className="uppercase text-xs">
            name
          </span>
          <span>
            {owner?.name || "N/A"}
          </span>
        </div>
        <div className="flex flex-col">
          <span className="uppercase text-xs">
            contact
          </span>
          <span>
            {owner?.contact || "N/A"}
          </span>
        </div>
      </DialogContent>
    </Dialog>
  );
}

export default OwnerInfoDialog;
