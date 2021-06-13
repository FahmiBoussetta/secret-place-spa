import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Establishment from './establishment';
import EstablishmentDetail from './establishment-detail';
import EstablishmentUpdate from './establishment-update';
import EstablishmentDeleteDialog from './establishment-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EstablishmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EstablishmentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EstablishmentDetail} />
      <ErrorBoundaryRoute path={match.url} component={Establishment} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EstablishmentDeleteDialog} />
  </>
);

export default Routes;
