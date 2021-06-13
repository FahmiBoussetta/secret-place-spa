import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEstablishment } from 'app/shared/model/establishment.model';
import { getEntities as getEstablishments } from 'app/entities/establishment/establishment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './manage.reducer';
import { IManage } from 'app/shared/model/manage.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IManageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ManageUpdate = (props: IManageUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { manageEntity, users, establishments, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/manage');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getEstablishments();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...manageEntity,
        ...values,
        userId: users.find(it => it.id.toString() === values.userIdId.toString()),
        establishmentId: establishments.find(it => it.id.toString() === values.establishmentIdId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="secretplacespaApp.manage.home.createOrEditLabel" data-cy="ManageCreateUpdateHeading">
            <Translate contentKey="secretplacespaApp.manage.home.createOrEditLabel">Create or edit a Manage</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : manageEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="manage-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="manage-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="manage-userId">
                  <Translate contentKey="secretplacespaApp.manage.userId">User Id</Translate>
                </Label>
                <AvInput id="manage-userId" data-cy="userId" type="select" className="form-control" name="userIdId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="manage-establishmentId">
                  <Translate contentKey="secretplacespaApp.manage.establishmentId">Establishment Id</Translate>
                </Label>
                <AvInput
                  id="manage-establishmentId"
                  data-cy="establishmentId"
                  type="select"
                  className="form-control"
                  name="establishmentIdId"
                >
                  <option value="" key="0" />
                  {establishments
                    ? establishments.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/manage" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  establishments: storeState.establishment.entities,
  manageEntity: storeState.manage.entity,
  loading: storeState.manage.loading,
  updating: storeState.manage.updating,
  updateSuccess: storeState.manage.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEstablishments,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ManageUpdate);
