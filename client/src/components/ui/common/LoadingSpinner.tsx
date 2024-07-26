import { cn } from "@/lib/utils";
import { Loader } from "lucide-react";

interface LoadingSpinnerProps {
  className?: string;
}

function LoadingSpinner({
  className,
}: LoadingSpinnerProps) {
  return (
    <Loader className={cn("-my-1 h-6 w-6 animate-spin text-stone-500", className)} />
  );
}

export default LoadingSpinner;
