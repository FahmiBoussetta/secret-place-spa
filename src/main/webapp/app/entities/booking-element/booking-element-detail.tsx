import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './booking-element.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBookingElementDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BookingElementDetail = (props: IBookingElementDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bookingElementEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bookingElementDetailsHeading">
          <Translate contentKey="secretplacespaApp.bookingElement.detail.title">BookingElement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bookingElementEntity.id}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="secretplacespaApp.bookingElement.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>
            {bookingElementEntity.startDate ? (
              <TextFormat value={bookingElementEntity.startDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="secretplacespaApp.bookingElement.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>
            {bookingElementEntity.endDate ? <TextFormat value={bookingElementEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="elementPrice">
              <Translate contentKey="secretplacespaApp.bookingElement.elementPrice">Element Price</Translate>
            </span>
            <UncontrolledTooltip target="elementPrice">
              <Translate contentKey="secretplacespaApp.bookingElement.help.elementPrice" />
            </UncontrolledTooltip>
          </dt>
          <dd>{bookingElementEntity.elementPrice}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.bookingElement.bookingId">Booking Id</Translate>
          </dt>
          <dd>{bookingElementEntity.bookingId ? bookingElementEntity.bookingId.id : ''}</dd>
          <dt>
            <Translate contentKey="secretplacespaApp.bookingElement.housingTemplateId">Housing Template Id</Translate>
          </dt>
          <dd>{bookingElementEntity.housingTemplateId ? bookingElementEntity.housingTemplateId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/booking-element" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/booking-element/${bookingElementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ bookingElement }: IRootState) => ({
  bookingElementEntity: bookingElement.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BookingElementDetail);
