import { PlusIcon } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { CARS_ROUTE, CREATE_CAR_ROUTE } from "@/lib/consts";

function CreateMakeButton() {
  const navigate = useNavigate();
  return (
    <button
      className="flex flex-col py-2 px-4 border"
      type="button"
      onClick={() => navigate(`/${CARS_ROUTE}/${CREATE_CAR_ROUTE}`)}
    >
      <img
        src="https://res.cloudinary.com/dbpwbih9m/image/upload/v1722368139/create-make_tze1ja.png"
        alt=""
        className="w-full"
      />
      <div className="flex items-center py-4">
        <PlusIcon size={24} strokeWidth={1.25} />
        <span>
          Add make
        </span>
      </div>
    </button>
  );
}

export default CreateMakeButton;
