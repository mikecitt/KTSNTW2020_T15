import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { CulturalOfferLocation } from '../model/culutral-offer-location';
import {environment} from '../../environments/environment.prod'
import * as Mapboxgl from 'mapbox-gl';


@Component({
  selector: 'app-map-item',
  templateUrl: './map-item.component.html',
  styleUrls: ['./map-item.component.scss']
})
export class MapItemComponent implements OnInit, OnChanges {

  @Input()
  culutralOffers: CulturalOfferLocation[];
  @Input()
  location: string;

  mapa: Mapboxgl.Map;
  markers: Mapboxgl.Marker[] = [];
  mapInitialized: boolean = false;

  constructor() {  }

  ngOnChanges(changes: SimpleChanges): void{
    if(this.mapInitialized){
      if(changes['culutralOffers']){
        this.removeMarkers();
        this.updateMarkers();
      }
      if(changes['location'])
        this.focusOnLocation();
    }
  }

  ngOnInit(): void {
      this.initializeMap();
      this.updateMarkers();
  }
  createMarkers() :void{

    this.culutralOffers.forEach(location => {
      const marker = new Mapboxgl.Marker({
        draggable: false
      }).setLngLat([location.longitude, location.latitude])
        .setPopup(new Mapboxgl.Popup().setHTML(`<h1 style="background-color:powderblue;">${location.address}</h1>`));

      this.markers.push(marker);
    });
  }
  initializeMap(): void{
    (Mapboxgl as any).accessToken = environment.mapboxKey;

    this.mapa = new Mapboxgl.Map({
      container: 'map-mapbox',
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [20.426773,44.9979649],   //LNG, LAT
      zoom: 7
      });

    this.mapInitialized = true;
  }
  updateMarkers(): void{
    this.createMarkers();
    //add markers on the map
    this.markers.forEach((marker) =>  marker.addTo(this.mapa));
  }

  removeMarkers(): void{
    this.markers.forEach((marker) => marker.remove());
    this.markers = [];
  }
  focusOnLocation(){
    console.log(this.location);
  }

}
