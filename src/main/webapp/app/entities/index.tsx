import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Booking from './booking';
import BookingElement from './booking-element';
import Favorites from './favorites';
import Rating from './rating';
import Establishment from './establishment';
import Manage from './manage';
import EstablishmentClosure from './establishment-closure';
import HousingTemplate from './housing-template';
import HousingClosure from './housing-closure';
import Photo from './photo';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}booking`} component={Booking} />
      <ErrorBoundaryRoute path={`${match.url}booking-element`} component={BookingElement} />
      <ErrorBoundaryRoute path={`${match.url}favorites`} component={Favorites} />
      <ErrorBoundaryRoute path={`${match.url}rating`} component={Rating} />
      <ErrorBoundaryRoute path={`${match.url}establishment`} component={Establishment} />
      <ErrorBoundaryRoute path={`${match.url}manage`} component={Manage} />
      <ErrorBoundaryRoute path={`${match.url}establishment-closure`} component={EstablishmentClosure} />
      <ErrorBoundaryRoute path={`${match.url}housing-template`} component={HousingTemplate} />
      <ErrorBoundaryRoute path={`${match.url}housing-closure`} component={HousingClosure} />
      <ErrorBoundaryRoute path={`${match.url}photo`} component={Photo} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
