import { Button } from "@/components/ui/common/shadcn/button";
import {
  Car,
  Wrench,
} from "lucide-react";
import { Make } from "@/lib/types";
import { useNavigate } from "react-router-dom";
import { MAKES_ROUTE } from "@/lib/consts";
import { useGetCarsCount } from "@/lib/api/MakesApi";
import LoadingSpinner from "@/components/ui/common/LoadingSpinner";
import MakeInfoDialog from "./MakeInfoDialog";

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
    data: totalCars,
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
      <div className="grid sm:grid-cols-2 gap-4">
        <Button
          variant="outline"
          onClick={navigateToMakeEditPage}
        >
          <Wrench className="text-stone-700" />
        </Button>
        <MakeInfoDialog make={make}>
          <Button variant="outline">
            <Car className="text-stone-700 mr-1" />
            {isLoadingGetCarsCount ? <LoadingSpinner /> : `(${totalCars})`}
          </Button>
        </MakeInfoDialog>
      </div>
    </div>
  );
}

export default MakeCard;
