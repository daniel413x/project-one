import {
  Link, useLocation,
} from "react-router-dom";
import {
  CARS_ROUTE, MAKES_ROUTE, MODELS_ROUTE,
  OWNERS_ROUTE,
} from "@/lib/consts";
import {
  Wrench,
  User,
  CarIcon,
} from "lucide-react";
import { cn } from "@/lib/utils";
import useIsAuthenticated from "@/lib/hooks/useIsAuthenticated";
import { ColorWheelIcon } from "@radix-ui/react-icons";

const links = [
  {
    to: `/${CARS_ROUTE}`,
    label: "Cars",
    icon: <Wrench className="w-5 h-5" strokeWidth={1.25} />,
  },
  {
    to: `/${MAKES_ROUTE}`,
    label: "Makes",
    icon: <ColorWheelIcon className="w-4 h-4" />,
  },
  {
    to: `/${MODELS_ROUTE}`,
    label: "Models",
    icon: <CarIcon className="w-6 h-6" strokeWidth={1.5} />,
  },
  {
    to: `/${OWNERS_ROUTE}`,
    label: "Owners",
    icon: <User className="w-5 h-5" />,
  },
];

interface MainNavProps {
  mobile?: boolean;
}

function MainNav({
  mobile,
}: MainNavProps) {
  const location = useLocation();
  const { pathname } = location;
  const py = 2;
  const isAuthenticated = useIsAuthenticated();
  return !isAuthenticated ? null : (
    <ul className={cn("flex", {
      "space-x-2 items-center": !mobile,
      "flex-col": mobile,
    })}
    >
      {links.map(({ to, icon, label }) => (
        <li key={to}>
          <Link
            to={to}
            // don't resize parent
            className={cn(`font-semibold relative group flex items-center gap-1 py-${py} -my-${py} px-3.5 rounded-md w-full`, {
              "bg-gray-500 text-white": to === pathname,
              "text-gray-700 hover:text-gray-500": to !== pathname,
            })}
          >
            {icon}
            <span>{label}</span>
          </Link>
        </li>
      ))}
    </ul>
  );
}

export default MainNav;
