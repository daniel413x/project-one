import { ReactNode } from "react";

interface StatUlProps {
  children: ReactNode;
}

function StatUl({
  children,
}: StatUlProps) {
  return (
    <ul className="grid md:grid-cols-3 lg:grid-cols-4 gap-4">
      {children}
    </ul>
  );
}

export default StatUl;
