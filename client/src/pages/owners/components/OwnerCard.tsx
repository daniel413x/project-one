import { Button } from "@/components/ui/common/shadcn/button";
import {
  Car,
  Trash2,
  Wrench,
} from "lucide-react";
import { Owner } from "@/lib/types";
import { useNavigate } from "react-router-dom";
import { USER_PLACEHOLDER_IMG_URL, OWNERS_ROUTE } from "@/lib/consts";
import { useGetCarsCount } from "@/lib/api/OwnersApi";
import LoadingSpinner from "@/components/ui/common/LoadingSpinner";
import { toKebabCase } from "@/lib/utils";
import OwnerInfoDialog from "./OwnerInfoDialog";
import DeleteOwnerAlertDialog from "./DeleteOwnerAlertDialog";

interface OwnerCardProps {
  owner: Owner;
}

function OwnerCard({
  owner,
}: OwnerCardProps) {
  const navigate = useNavigate();
  const navigateToOwnerEditPage = () => {
    navigate(`/${OWNERS_ROUTE}/${owner.id}`);
  };
  const {
    data: carsCount,
    isLoading: isLoadingGetCarsCount,
  } = useGetCarsCount(owner.id);
  return (
    <div className="flex flex-col py-2 px-4 border">
      <img
        src={USER_PLACEHOLDER_IMG_URL}
        alt=""
        className="w-full"
      />
      <div className="flex flex-col py-4">
        <span className="break-all sm:break-normal sm:truncate mb-1">
          {owner.name}
        </span>
      </div>
      <div className="grid sm:grid-cols-3 gap-4">
        <Button
          variant="outline"
          onClick={navigateToOwnerEditPage}
        >
          <Wrench className="text-stone-700" />
        </Button>
        <OwnerInfoDialog owner={owner} carsCount={carsCount}>
          <Button variant="outline">
            <Car className="text-stone-700 mr-1" />
            {isLoadingGetCarsCount ? <LoadingSpinner /> : `(${carsCount})`}
          </Button>
        </OwnerInfoDialog>
        <DeleteOwnerAlertDialog owner={owner} carsCount={carsCount}>
          <Button variant="outline" id={`delete-owner-${toKebabCase(owner.name)}`}>
            <Trash2 className="text-red-700 mr-1" />
          </Button>
        </DeleteOwnerAlertDialog>
      </div>
    </div>
  );
}

export default OwnerCard;
