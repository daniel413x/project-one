import { Button } from "@/components/ui/common/shadcn/button";
import {
  Car,
  Trash2,
  Wrench,
} from "lucide-react";
import { Model } from "@/lib/types";
import { useNavigate } from "react-router-dom";
import { CAR_PLACEHOLDER_IMG_URL, MODELS_ROUTE } from "@/lib/consts";
import { useGetCarsCount } from "@/lib/api/ModelsApi";
import LoadingSpinner from "@/components/ui/common/LoadingSpinner";
import ModelInfoDialog from "./ModelInfoDialog";
import DeleteModelAlertDialog from "./DeleteModelAlertDialog";

interface ModelCardProps {
  model: Model;
}

function ModelCard({
  model,
}: ModelCardProps) {
  const navigate = useNavigate();
  const navigateToModelEditPage = () => {
    navigate(`/${MODELS_ROUTE}/${model.id}`);
  };
  const {
    data: carsCount,
    isLoading: isLoadingGetCarsCount,
  } = useGetCarsCount(model.name);
  return (
    <div className="flex flex-col py-2 px-4 border">
      <img
        src={CAR_PLACEHOLDER_IMG_URL}
        alt=""
        className="w-full"
      />
      <div className="flex flex-col py-4">
        <span className="break-all sm:break-normal sm:truncate mb-1">
          {model.name}
        </span>
      </div>
      <div className="grid sm:grid-cols-3 gap-4">
        <Button
          variant="outline"
          onClick={navigateToModelEditPage}
        >
          <Wrench className="text-stone-700" />
        </Button>
        <ModelInfoDialog model={model} carsCount={carsCount}>
          <Button variant="outline">
            <Car className="text-stone-700 mr-1" />
            {isLoadingGetCarsCount ? <LoadingSpinner /> : `(${carsCount})`}
          </Button>
        </ModelInfoDialog>
        <DeleteModelAlertDialog model={model} carsCount={carsCount}>
          <Button variant="outline">
            <Trash2 className="text-red-700 mr-1" />
          </Button>
        </DeleteModelAlertDialog>
      </div>
    </div>
  );
}

export default ModelCard;
