import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Manage from './manage';
import ManageDetail from './manage-detail';
import ManageUpdate from './manage-update';
import ManageDeleteDialog from './manage-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ManageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ManageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ManageDetail} />
      <ErrorBoundaryRoute path={match.url} component={Manage} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ManageDeleteDialog} />
  </>
);

export default Routes;
