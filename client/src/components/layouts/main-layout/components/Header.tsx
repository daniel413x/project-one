import { Link } from "react-router-dom";
import logo from "@/assets/logo.png";
import { DASHBOARD_ROUTE } from "@/lib/consts";
import useIsAuthenticated from "@/lib/hooks/useIsAuthenticated";
import MobileNav from "./MobileNav";
import MainNav from "./MainNav";
import UsernameMenu from "./UsernameMenu";

function Header() {
  const isAuthenticated = useIsAuthenticated();
  return (
    <header
      className="border-b-2 border-b-gray-500 py-6 bg-white h-[86px]"
    >
      <div className="container flex mx-auto justify-between items-center">
        <Link
          className="text-3xl font-bold tracking-light text-gray-500 flex-grow flex-shrink-0 basis-auto"
          to={isAuthenticated ? `/${DASHBOARD_ROUTE}` : "/"}
        >
          <img
            src={logo}
            alt="Company logo"
          />
        </Link>
        <nav className="hidden md:flex -my-16 h-16 overflow-x-auto max-w-100 flex-grow flex-shrink basis-auto">
          <MainNav />
        </nav>
        {!isAuthenticated ? null : (
          <>
            <div className="md:hidden" data-testid="mobile-menu">
              <MobileNav />
            </div>
            <div className="hidden md:block space-x-2 items-center" data-testid="non-mobile-menu">
              <UsernameMenu />
            </div>
          </>
        )}
      </div>
    </header>
  );
}

export default Header;
