import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './housing-closure.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHousingClosureDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HousingClosureDetail = (props: IHousingClosureDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { housingClosureEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="housingClosureDetailsHeading">
          <Translate contentKey="secretplacespaApp.housingClosure.detail.title">HousingClosure</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{housingClosureEntity.id}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="secretplacespaApp.housingClosure.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {housingClosureEntity.startDate ? (
              <TextFormat value={housingClosureEntity.startDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="secretplacespaApp.housingClosure.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {housingClosureEntity.endDate ? <TextFormat value={housingClosureEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="cause">
              <Translate contentKey="secretplacespaApp.housingClosure.cause">Cause</Translate>
            </span>
          </dt>
          <dd>{housingClosureEntity.cause}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.housingClosure.establishmentId">Establishment Id</Translate>
          </dt>
          <dd>{housingClosureEntity.establishmentId ? housingClosureEntity.establishmentId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/housing-closure" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/housing-closure/${housingClosureEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ housingClosure }: IRootState) => ({
  housingClosureEntity: housingClosure.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HousingClosureDetail);
