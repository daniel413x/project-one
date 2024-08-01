import { PlusIcon } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { CAR_PLACEHOLDER_IMG_URL, MODELS_ROUTE } from "@/lib/consts";

function CreateModelButton() {
  const navigate = useNavigate();
  return (
    <button
      className="flex flex-col py-2 px-4 border"
      type="button"
      onClick={() => navigate(`/${MODELS_ROUTE}/create`)}
    >
      <img
        src={CAR_PLACEHOLDER_IMG_URL}
        alt=""
        className="w-full"
      />
      <div className="flex items-center py-4">
        <PlusIcon size={24} strokeWidth={1.25} />
        <span>
          Add model
        </span>
      </div>
    </button>
  );
}

export default CreateModelButton;
