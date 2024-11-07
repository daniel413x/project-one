import { ReactNode } from "react";
import { Owner } from "@/lib/types";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTrigger,
  DialogTitle,
} from "@/components/ui/common/shadcn/dialog";
import ModalKeyValuePair from "@/components/ui/common/ModalKeyValuePair";

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
        <ModalKeyValuePair k="name" value={owner?.name || "N/A"} />
        <ModalKeyValuePair k="contact" value={owner?.contact || "N/A"} />
      </DialogContent>
    </Dialog>
  );
}

export default OwnerInfoDialog;
