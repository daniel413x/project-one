import { ChevronDown, ChevronUp } from "lucide-react";
import { ReactNode, useState } from "react";

interface CollapsibleSectionProps {
  children: ReactNode;
  header: string;
}

function CollapsibleSection({
  children,
  header,
}: CollapsibleSectionProps) {
  const [show, setShow] = useState<boolean>(true);
  return (
    <section>
      <div className="flex space-between items-center my-10">
        <h2 className="text-xl font-bold flex-1">
          {header}
        </h2>
        <button className="p-4 -m-4" type="button" onClick={() => setShow(!show)}>
          {show ? <ChevronUp /> : <ChevronDown />}
        </button>
      </div>
      {show ? children : null}
    </section>
  );
}

export default CollapsibleSection;
