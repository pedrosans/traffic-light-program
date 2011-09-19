package tcc.model;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.MapperWrapper;

public class XStreamFacade {
	public static XStream xStream = new XStream() {
		protected MapperWrapper wrapMapper(MapperWrapper next) {
			return new MapperWrapper(next) {
				public boolean shouldSerializeMember(Class definedIn, String fieldName) {
					return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
				}

			};
		}
	};
	static {
		xStream.setMode(XStream.NO_REFERENCES);
		xStream.processAnnotations(Net.class);
		xStream.processAnnotations(TLLogic.class);
		xStream.processAnnotations(TLLogic.Phase.class);
		xStream.processAnnotations(TripInfos.class);
		xStream.processAnnotations(TripInfos.TripInfo.class);
	}
}
