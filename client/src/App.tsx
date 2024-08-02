import {
  Navigate, Route, BrowserRouter as Router, Routes,
} from "react-router-dom";
import MainLayout from "./components/layouts/main-layout/MainLayout";
import {
  CARS_ROUTE,
  DASHBOARD_ROUTE,
  MAKES_ROUTE,
  MODELS_ROUTE,
  OWNERS_ROUTE,
} from "./lib/consts";
import RootPage from "./pages/root/RootPage";
import DashboardPage from "./pages/dashboard/DashboardPage";
import CarsPage from "./pages/cars/CarsPage";
import CreateCarPage from "./pages/cars/routes/create-car/CreateCarPage";
import PreviousHistoryItemProvider from "./components/providers/PreviousHistoryItemProvider";
import MakesPage from "./pages/makes/MakesPage";
import CreateMakePage from "./pages/makes/routes/create-makes/CreateMakePage";
import ModelsPage from "./pages/models/ModelsPage";
import CreateModelPage from "./pages/models/routes/create-models/CreateModelPage";
import CreateOwnerPage from "./pages/owners/routes/create-owners/CreateOwnerPage";
import OwnersPage from "./pages/owners/OwnersPage";

function App() {
  return (
    <Router>
      <PreviousHistoryItemProvider>
        <Routes>
          <Route
            path="/"
            element={(
              <MainLayout noContainer>
                <RootPage />
              </MainLayout>
            )}
          />
          <Route
            path={`/${MAKES_ROUTE}`}
            element={(
              <MainLayout>
                <MakesPage />
              </MainLayout>
          )}
          />
          <Route
            path={`/${CARS_ROUTE}`}
            element={(
              <MainLayout>
                <CarsPage />
              </MainLayout>
          )}
          />
          <Route
            path={`/${MODELS_ROUTE}`}
            element={(
              <MainLayout>
                <ModelsPage />
              </MainLayout>
          )}
          />
          <Route
            path={`/${OWNERS_ROUTE}`}
            element={(
              <MainLayout>
                <OwnersPage />
              </MainLayout>
          )}
          />
          <Route
            path={`/${CARS_ROUTE}/:id`}
            element={(
              <MainLayout>
                <CreateCarPage />
              </MainLayout>
          )}
          />
          <Route
            path={`/${MAKES_ROUTE}/:id`}
            element={(
              <MainLayout>
                <CreateMakePage />
              </MainLayout>
          )}
          />
          <Route
            path={`/${OWNERS_ROUTE}/:id`}
            element={(
              <MainLayout>
                <CreateOwnerPage />
              </MainLayout>
          )}
          />
          <Route
            path={`/${MODELS_ROUTE}/:id`}
            element={(
              <MainLayout>
                <CreateModelPage />
              </MainLayout>
          )}
          />
          <Route
            path={`/${DASHBOARD_ROUTE}`}
            element={(
              <MainLayout>
                <DashboardPage />
              </MainLayout>
          )}
          />
          <Route
            path="*"
            element={(
              <Navigate
                to="/"
              />
            )}
          />
        </Routes>
      </PreviousHistoryItemProvider>
    </Router>
  );
}

export default App;
