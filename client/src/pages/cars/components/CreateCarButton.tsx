import { PlusIcon } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { CARS_ROUTE, CAR_PLACEHOLDER_IMG_URL, CREATE_ROUTE } from "@/lib/consts";

function CreateCarButton() {
  const navigate = useNavigate();
  return (
    <button
      className="flex flex-col py-2 px-4 border h-max"
      type="button"
      onClick={() => navigate(`/${CARS_ROUTE}/${CREATE_ROUTE}`)}
    >
      <img
        src={CAR_PLACEHOLDER_IMG_URL}
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
