import { ReactNode } from "react";
import Header from "./components/Header";
import Footer from "./components/Footer";

interface MainLayoutProps {
  children: ReactNode;
  noContainer?: boolean;
}

function MainLayout({
  children,
  noContainer,
}: MainLayoutProps) {
  return (
    <div
      className="flex flex-col min-h-screen w-full"
      data-testid="main-layout"
    >
      <div
        className="fixed min-h-screen w-full bg-no-repeat bg-cover bg-center -z-10"
        style={{ backgroundImage: "url(https://res.cloudinary.com/dbpwbih9m/image/upload/v1722613784/bg-7w_km6vwz.png)" }}
      />
      <Header />
      {noContainer ? children : (
        <div className="container mx-auto flex-1 py-10">
          {children}
        </div>
      )}
      <Footer />
    </div>
  );
}

export default MainLayout;
