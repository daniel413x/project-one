import { PlusIcon } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { USER_PLACEHOLDER_IMG_URL, OWNERS_ROUTE } from "@/lib/consts";

function CreateOwnerButton() {
  const navigate = useNavigate();
  return (
    <button
      className="flex flex-col py-2 px-4 border h-max"
      type="button"
      onClick={() => navigate(`/${OWNERS_ROUTE}/create`)}
    >
      <img
        src={USER_PLACEHOLDER_IMG_URL}
        alt=""
        className="w-full"
      />
      <div className="flex items-center py-4">
        <PlusIcon size={24} strokeWidth={1.25} />
        <span>
          Add owner
        </span>
      </div>
    </button>
  );
}

export default CreateOwnerButton;
