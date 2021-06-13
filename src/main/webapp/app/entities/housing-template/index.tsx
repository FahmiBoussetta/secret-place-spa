import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import HousingTemplate from './housing-template';
import HousingTemplateDetail from './housing-template-detail';
import HousingTemplateUpdate from './housing-template-update';
import HousingTemplateDeleteDialog from './housing-template-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HousingTemplateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HousingTemplateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HousingTemplateDetail} />
      <ErrorBoundaryRoute path={match.url} component={HousingTemplate} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HousingTemplateDeleteDialog} />
  </>
);

export default Routes;
