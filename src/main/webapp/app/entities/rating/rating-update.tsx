import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label, UncontrolledTooltip } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IEstablishment } from 'app/shared/model/establishment.model';
import { getEntities as getEstablishments } from 'app/entities/establishment/establishment.reducer';
import { getEntity, updateEntity, createEntity, reset } from './rating.reducer';
import { IRating } from 'app/shared/model/rating.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRatingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RatingUpdate = (props: IRatingUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { ratingEntity, users, establishments, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/rating');
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
    values.ratingDate = convertDateTimeToServer(values.ratingDate);

    if (errors.length === 0) {
      const entity = {
        ...ratingEntity,
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
          <h2 id="secretplacespaApp.rating.home.createOrEditLabel" data-cy="RatingCreateUpdateHeading">
            <Translate contentKey="secretplacespaApp.rating.home.createOrEditLabel">Create or edit a Rating</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : ratingEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="rating-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="rating-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="rateLabel" for="rating-rate">
                  <Translate contentKey="secretplacespaApp.rating.rate">Rate</Translate>
                </Label>
                <AvField
                  id="rating-rate"
                  data-cy="rate"
                  type="string"
                  className="form-control"
                  name="rate"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    min: { value: 1, errorMessage: translate('entity.validation.min', { min: 1 }) },
                    max: { value: 5, errorMessage: translate('entity.validation.max', { max: 5 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
                <UncontrolledTooltip target="rateLabel">
                  <Translate contentKey="secretplacespaApp.rating.help.rate" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="commentLabel" for="rating-comment">
                  <Translate contentKey="secretplacespaApp.rating.comment">Comment</Translate>
                </Label>
                <AvField
                  id="rating-comment"
                  data-cy="comment"
                  type="text"
                  name="comment"
                  validate={{
                    maxLength: { value: 250, errorMessage: translate('entity.validation.maxlength', { max: 250 }) },
                  }}
                />
                <UncontrolledTooltip target="commentLabel">
                  <Translate contentKey="secretplacespaApp.rating.help.comment" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label id="ratingDateLabel" for="rating-ratingDate">
                  <Translate contentKey="secretplacespaApp.rating.ratingDate">Rating Date</Translate>
                </Label>
                <AvInput
                  id="rating-ratingDate"
                  data-cy="ratingDate"
                  type="datetime-local"
                  className="form-control"
                  name="ratingDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.ratingEntity.ratingDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
                <UncontrolledTooltip target="ratingDateLabel">
                  <Translate contentKey="secretplacespaApp.rating.help.ratingDate" />
                </UncontrolledTooltip>
              </AvGroup>
              <AvGroup>
                <Label for="rating-userId">
                  <Translate contentKey="secretplacespaApp.rating.userId">User Id</Translate>
                </Label>
                <AvInput id="rating-userId" data-cy="userId" type="select" className="form-control" name="userIdId">
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
                <Label for="rating-establishmentId">
                  <Translate contentKey="secretplacespaApp.rating.establishmentId">Establishment Id</Translate>
                </Label>
                <AvInput
                  id="rating-establishmentId"
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
              <Button tag={Link} id="cancel-save" to="/rating" replace color="info">
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
  ratingEntity: storeState.rating.entity,
  loading: storeState.rating.loading,
  updating: storeState.rating.updating,
  updateSuccess: storeState.rating.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(RatingUpdate);
