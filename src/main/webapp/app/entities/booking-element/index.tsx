import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BookingElement from './booking-element';
import BookingElementDetail from './booking-element-detail';
import BookingElementUpdate from './booking-element-update';
import BookingElementDeleteDialog from './booking-element-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BookingElementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BookingElementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BookingElementDetail} />
      <ErrorBoundaryRoute path={match.url} component={BookingElement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BookingElementDeleteDialog} />
  </>
);

export default Routes;
