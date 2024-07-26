import { cn } from "@/lib/utils";
import { ReactNode } from "react";

interface ContentFrameProps {
  children: ReactNode;
  mt?: boolean;
  mb?: boolean;
}

function ContentFrame({
  children,
  mt,
  mb,
}: ContentFrameProps) {
  return (
    <div className={cn("bg-white border-2 border-gray-500 md:p-10 py-2 px-3", {
      "mt-10": mt,
      "mb-10": mb,
    })}
    >
      {children}
    </div>
  );
}

export default ContentFrame;
