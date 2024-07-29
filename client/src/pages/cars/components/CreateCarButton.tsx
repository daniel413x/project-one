import { PlusIcon } from "lucide-react";
import carPlaceholder from "@/assets/car.png";
import { useNavigate } from "react-router-dom";
import { CARS_ROUTE, CREATE_CAR_ROUTE } from "@/lib/consts";

function CreateCarButton() {
  const navigate = useNavigate();
  return (
    <button
      className="flex flex-col py-2 px-4 border"
      type="button"
      onClick={() => navigate(`/${CARS_ROUTE}/${CREATE_CAR_ROUTE}`)}
    >
      <img
        src={carPlaceholder}
        alt=""
        className="w-full"
      />
      <div className="flex items-center py-4">
        <PlusIcon size={24} strokeWidth={1.25} />
        <span>
          Create new car
        </span>
      </div>
    </button>
  );
}

export default CreateCarButton;
