import { Button } from "@/components/ui/common/shadcn/button";
import {
  Car,
  Trash2,
  Wrench,
} from "lucide-react";
import { Make } from "@/lib/types";
import { useNavigate } from "react-router-dom";
import { MAKES_ROUTE } from "@/lib/consts";
import { useGetCarsCount } from "@/lib/api/MakesApi";
import LoadingSpinner from "@/components/ui/common/LoadingSpinner";
import MakeInfoDialog from "./MakeInfoDialog";
import DeleteMakeDialog from "./DeleteMakeDialog";

interface MakeCardProps {
  make: Make;
}

function MakeCard({
  make,
}: MakeCardProps) {
  const navigate = useNavigate();
  const navigateToMakeEditPage = () => {
    navigate(`/${MAKES_ROUTE}/${make.id}`);
  };
  const {
    data: carsCount,
    isLoading: isLoadingGetCarsCount,
  } = useGetCarsCount(make.name);
  return (
    <div className="flex flex-col py-2 px-4 border">
      <img
        src={make.logoUrl}
        alt=""
        className="w-full"
      />
      <div className="flex flex-col py-4">
        <span className="break-all sm:break-normal sm:truncate mb-1">
          {make.name}
        </span>
      </div>
      <div className="grid sm:grid-cols-3 gap-4">
        <Button
          variant="outline"
          onClick={navigateToMakeEditPage}
          aria-label="Navigate to make edit page"
        >
          <Wrench className="text-stone-700" />
        </Button>
        <MakeInfoDialog make={make}>
          <Button
            variant="outline"
            aria-label="Open make information box"
          >
            <Car className="text-stone-700 mr-1" />
            {isLoadingGetCarsCount ? <LoadingSpinner /> : `(${carsCount})`}
          </Button>
        </MakeInfoDialog>
        <DeleteMakeDialog make={make} carsCount={carsCount}>
          <Button
            variant="outline"
            aria-label="Open delete make confirmation box"
          >
            <Trash2 className="text-red-700 mr-1" />
          </Button>
        </DeleteMakeDialog>
      </div>
    </div>
  );
}

export default MakeCard;
